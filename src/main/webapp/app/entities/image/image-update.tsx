import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, openFile, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPost } from 'app/shared/model/post.model';
import { getEntities as getPosts } from 'app/entities/post/post.reducer';
import { getEntity, updateEntity, createEntity, setBlob, reset } from './image.reducer';
import { IImage } from 'app/shared/model/image.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IImageUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ImageUpdate = (props: IImageUpdateProps) => {
  const [postId, setPostId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { imageEntity, posts, loading, updating } = props;

  const { content, contentContentType } = imageEntity;

  const handleClose = () => {
    props.history.push('/image');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getPosts();
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...imageEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="listitApp.image.home.createOrEditLabel">
            <Translate contentKey="listitApp.image.home.createOrEditLabel">Create or edit a Image</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : imageEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="image-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="image-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="urlLabel" for="image-url">
                  <Translate contentKey="listitApp.image.url">Url</Translate>
                </Label>
                <AvField id="image-url" type="text" name="url" />
              </AvGroup>
              <AvGroup>
                <AvGroup>
                  <Label id="contentLabel" for="content">
                    <Translate contentKey="listitApp.image.content">Content</Translate>
                  </Label>
                  <br />
                  {content ? (
                    <div>
                      {contentContentType ? (
                        <a onClick={openFile(contentContentType, content)}>
                          <img src={`data:${contentContentType};base64,${content}`} style={{ maxHeight: '100px' }} />
                        </a>
                      ) : null}
                      <br />
                      <Row>
                        <Col md="11">
                          <span>
                            {contentContentType}, {byteSize(content)}
                          </span>
                        </Col>
                        <Col md="1">
                          <Button color="danger" onClick={clearBlob('content')}>
                            <FontAwesomeIcon icon="times-circle" />
                          </Button>
                        </Col>
                      </Row>
                    </div>
                  ) : null}
                  <input id="file_content" type="file" onChange={onBlobChange(true, 'content')} accept="image/*" />
                  <AvInput type="hidden" name="content" value={content} />
                </AvGroup>
              </AvGroup>
              <AvGroup check>
                <Label id="isTopLabel">
                  <AvInput id="image-isTop" type="checkbox" className="form-check-input" name="isTop" />
                  <Translate contentKey="listitApp.image.isTop">Is Top</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label for="image-post">
                  <Translate contentKey="listitApp.image.post">Post</Translate>
                </Label>
                <AvInput id="image-post" type="select" className="form-control" name="postId">
                  <option value="" key="0" />
                  {posts
                    ? posts.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/image" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  posts: storeState.post.entities,
  imageEntity: storeState.image.entity,
  loading: storeState.image.loading,
  updating: storeState.image.updating,
  updateSuccess: storeState.image.updateSuccess,
});

const mapDispatchToProps = {
  getPosts,
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ImageUpdate);
