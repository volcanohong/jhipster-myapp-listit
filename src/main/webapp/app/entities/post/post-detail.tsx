import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './post.reducer';
import { IPost } from 'app/shared/model/post.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPostDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PostDetail = (props: IPostDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { postEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="listitApp.post.detail.title">Post</Translate> [<b>{postEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="listitApp.post.name">Name</Translate>
            </span>
          </dt>
          <dd>{postEntity.name}</dd>
          <dt>
            <span id="detail">
              <Translate contentKey="listitApp.post.detail">Detail</Translate>
            </span>
          </dt>
          <dd>{postEntity.detail}</dd>
          <dt>
            <span id="searchText">
              <Translate contentKey="listitApp.post.searchText">Search Text</Translate>
            </span>
          </dt>
          <dd>{postEntity.searchText}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="listitApp.post.price">Price</Translate>
            </span>
          </dt>
          <dd>{postEntity.price}</dd>
          <dt>
            <span id="priceNegotiable">
              <Translate contentKey="listitApp.post.priceNegotiable">Price Negotiable</Translate>
            </span>
          </dt>
          <dd>{postEntity.priceNegotiable ? 'true' : 'false'}</dd>
          <dt>
            <span id="condition">
              <Translate contentKey="listitApp.post.condition">Condition</Translate>
            </span>
          </dt>
          <dd>{postEntity.condition}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="listitApp.post.status">Status</Translate>
            </span>
          </dt>
          <dd>{postEntity.status}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="listitApp.post.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>{postEntity.createdDate ? <TextFormat value={postEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="lastModifiedDate">
              <Translate contentKey="listitApp.post.lastModifiedDate">Last Modified Date</Translate>
            </span>
          </dt>
          <dd>
            {postEntity.lastModifiedDate ? <TextFormat value={postEntity.lastModifiedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="lastReviewedData">
              <Translate contentKey="listitApp.post.lastReviewedData">Last Reviewed Data</Translate>
            </span>
          </dt>
          <dd>
            {postEntity.lastReviewedData ? <TextFormat value={postEntity.lastReviewedData} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="reviewedCount">
              <Translate contentKey="listitApp.post.reviewedCount">Reviewed Count</Translate>
            </span>
          </dt>
          <dd>{postEntity.reviewedCount}</dd>
          <dt>
            <Translate contentKey="listitApp.post.location">Location</Translate>
          </dt>
          <dd>{postEntity.locationId ? postEntity.locationId : ''}</dd>
          <dt>
            <Translate contentKey="listitApp.post.category">Category</Translate>
          </dt>
          <dd>{postEntity.categoryId ? postEntity.categoryId : ''}</dd>
          <dt>
            <Translate contentKey="listitApp.post.user">User</Translate>
          </dt>
          <dd>{postEntity.userId ? postEntity.userId : ''}</dd>
        </dl>
        <Button tag={Link} to="/post" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/post/${postEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ post }: IRootState) => ({
  postEntity: post.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PostDetail);
