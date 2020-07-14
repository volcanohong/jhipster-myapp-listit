import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './post-category.reducer';
import { IPostCategory } from 'app/shared/model/post-category.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPostCategoryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PostCategoryDetail = (props: IPostCategoryDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { postCategoryEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="listitApp.postCategory.detail.title">PostCategory</Translate> [<b>{postCategoryEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="listitApp.postCategory.name">Name</Translate>
            </span>
          </dt>
          <dd>{postCategoryEntity.name}</dd>
          <dt>
            <span id="isEnabled">
              <Translate contentKey="listitApp.postCategory.isEnabled">Is Enabled</Translate>
            </span>
          </dt>
          <dd>{postCategoryEntity.isEnabled ? 'true' : 'false'}</dd>
          <dt>
            <span id="maxImages">
              <Translate contentKey="listitApp.postCategory.maxImages">Max Images</Translate>
            </span>
          </dt>
          <dd>{postCategoryEntity.maxImages}</dd>
          <dt>
            <span id="validDays">
              <Translate contentKey="listitApp.postCategory.validDays">Valid Days</Translate>
            </span>
          </dt>
          <dd>{postCategoryEntity.validDays}</dd>
          <dt>
            <Translate contentKey="listitApp.postCategory.category">Category</Translate>
          </dt>
          <dd>{postCategoryEntity.categoryId ? postCategoryEntity.categoryId : ''}</dd>
        </dl>
        <Button tag={Link} to="/post-category" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/post-category/${postCategoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ postCategory }: IRootState) => ({
  postCategoryEntity: postCategory.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PostCategoryDetail);
